package com.example.pricelist.service;

import com.example.pricelist.model.Notification;
import com.example.pricelist.model.Pricelist;
import com.example.pricelist.model.VehicleDiscount;
import com.example.pricelist.repository.PricelistRepository;
import com.example.pricelist.repository.VehicleDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class PricelistService {

    @Autowired
    private PricelistRepository pricelistRepository;

    @Autowired
    private VehicleDiscountRepository vehicleDiscountRepository;

    public List<Pricelist> getAll() { return pricelistRepository.findAll(); }

    public List<Pricelist> getAllByVehicle(Long vehicleId) {
        List<Pricelist> pricelistList = null;
        try{
            pricelistList = pricelistRepository.findByVehicleId(vehicleId);
        }
        catch(Exception e){

        }
        return pricelistList;
    }

    public Notification createPricelist(Pricelist pricelist) {
        Notification notification = new Notification("Failed to create pricelist.");
        try{
            pricelist.setId(null);

            if (priceInvalid(pricelist)){
                notification.setText("Invalid price.");
                return notification;
            }

            if (discountInvalid(pricelist.getVehicleDiscount())){
                notification.setText("Invalid discount.");
                return notification;
            }

            if (dateRangeExists(pricelist, "CREATE")){
                notification.setText("Already defined price for requested date. If you want to create pricelist item please first delete all items which have requested date range.");
                return notification;
            }
            if (dateRangeOutdated(pricelist)){
                notification.setText("Date range outdated.");
                return notification;
            }
            if (dateRangeInvalid(pricelist)){
                notification.setText("Date range invalid.");
                return notification;
            }
            pricelistRepository.save(pricelist);
            notification.setText("Created pricelist for requested vehicle.");
            return notification;
        }
        catch(Exception e){

        }
        return notification;
    }

    public Notification updatePricelist(Pricelist pricelist) {
        Notification notification = new Notification("Failed to update pricelist.");
        try{
            if (!pricelistRepository.findById(pricelist.getId()).isPresent()){
                notification.setText("Pricelist id does not exist.");
                return notification;
            }

            if (priceInvalid(pricelist)){
                notification.setText("Invalid price.");
                return notification;
            }

            if (discountInvalid(pricelist.getVehicleDiscount()) || !vehicleDiscountRepository.findById(pricelist.getVehicleDiscount().getId()).isPresent()){
                notification.setText("Invalid discount.");
                return notification;
            }

            if (dateRangeExists(pricelist, "UPDATE") && pricelistRepository.findByVehicleId(pricelist.getVehicleId()).size() != 1){
                notification.setText("Already defined price for requested date. If you want to update pricelist item please first delete all items which have requested date range.");
                return notification;
            }
            if (dateRangeOutdated(pricelist)){
                notification.setText("Date range outdated.");
                return notification;
            }
            if (dateRangeInvalid(pricelist)){
                notification.setText("Date range invalid.");
                return notification;
            }
            pricelistRepository.save(pricelist);
            vehicleDiscountRepository.save(pricelist.getVehicleDiscount());
            notification.setText("Updated pricelist for requested vehicle.");
            return notification;
        }
        catch(Exception e){

        }
        return notification;
    }

    public Notification deletePricelist(Long pricelistId) {
        Notification notification = new Notification("Failed to delete pricelist.");
        try{
            if (!pricelistRepository.findById(pricelistId).isPresent()){
                notification.setText("Pricelist does not exist.");
                return notification;
            }
            Long vehicleId = pricelistRepository.findById(pricelistId).get().getVehicleId();
            if (pricelistRepository.findByVehicleId(vehicleId).size() == 1){
                notification.setText("Vehicle must have at least one pricelist defined.");
                return notification;
            }
            pricelistRepository.deleteById(pricelistId);
            notification.setText("Pricelist deleted.");
            return notification;
        }
        catch(Exception e){

        }
        return notification;
    }

    public boolean dateRangeExists(Pricelist pricelist, String operation){
        List<Pricelist> pricelists = getAllByVehicle(pricelist.getVehicleId());

        for (Pricelist p : pricelists){
            if ((pricelist.getStartDate().isAfter(p.getStartDate()) && pricelist.getStartDate().isBefore(p.getEndDate()))
            || (pricelist.getEndDate().isAfter(p.getStartDate()) && pricelist.getEndDate().isBefore(p.getEndDate()))
            || (pricelist.getStartDate().isBefore(p.getStartDate()) && pricelist.getEndDate().isAfter(p.getEndDate()))){
                if (operation.equals("CREATE")){
                    if (pricelist.getStartDate().equals(p.getStartDate()) && pricelist.getEndDate().equals(p.getEndDate())){
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean dateRangeOutdated(Pricelist pricelist){
        if (pricelist.getStartDate().isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

    public boolean dateRangeInvalid(Pricelist pricelist){
        if (pricelist.getStartDate().isAfter(pricelist.getEndDate())){
            return true;
        }
        return false;
    }

    public boolean discountInvalid(VehicleDiscount vehicleDiscount){
        if(vehicleDiscount.getDiscount() < 1 || vehicleDiscount.getDiscount() > 100
        || vehicleDiscount.getNumDays() < 0){
            return true;
        }
        return false;
    }

    public boolean priceInvalid(Pricelist pricelist){
        if(pricelist.getPrice() < 0 || pricelist.getPriceByMile() < 0 || pricelist.getPriceCollision() < 0){
            return true;
        }
        return false;
    }

}