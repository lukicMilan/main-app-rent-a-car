import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/core/services/cart.service';
import { Cart } from 'src/app/shared/models/cart/Cart';
import { Request } from 'src/app/shared/models/cart/Request';
import { MatTableDataSource, MatDialog, MatSnackBar } from '@angular/material';
import { RequestAndVehicle } from 'src/app/shared/models/cart/RequestAndVehicle';
import { DetailedCart } from 'src/app/shared/models/cart/DetailedCart';
import { Bundle } from 'src/app/shared/models/cart/Bundle';
import { ViewPriceListComponent } from '../price-list/view-price-list/view-price-list.component';
import { VehicleDetailsComponent } from '../vehicle-details/vehicle-details.component';
import { BundleAndVehicle } from 'src/app/shared/models/cart/BundleAndVehicle';
import { MatListModule } from '@angular/material/list';
import { PricelistService } from 'src/app/core/services/pricelist.service';
import { Pricelist } from 'src/app/shared/models/pricelist/Pricelist';

@Component({
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {

  emptyCart: boolean
  cart: DetailedCart
  requests: RequestAndVehicle[] = []
  bundleList: BundleAndVehicle[] = []
  dataSourceRequests: MatTableDataSource<RequestAndVehicle>;
  dataSourceBundle: MatTableDataSource<BundleAndVehicle>;
  displayedColumns: string[] = ['make', 'model', 'price', 'owner', 'details', 'prices', 'remove'];
  displayedColumns2: string[] = ['make', 'model', 'price', 'owner', 'remove'];
  price: number = 0

  constructor(private cartService: CartService, public dialog: MatDialog, private _snackBar: MatSnackBar, private pricelistService: PricelistService) { }

  ngOnInit() {
    this.cart = this.cartService.getCart()
    this.requests = this.cart.requests
    this.bundleList = this.cart.bundles
    this.dataSourceRequests = new MatTableDataSource<RequestAndVehicle>(this.requests);
    this.dataSourceBundle = new MatTableDataSource<BundleAndVehicle>(this.bundleList);

    if (this.requests.length == 0 && this.bundleList.length == 0)
      this.emptyCart = true
    else {
      this.emptyCart = false
      this.calculateTotalPrice()
    }
    console.log(this.cart)
  }

  removeFromBundle(element: RequestAndVehicle, bundle: BundleAndVehicle) {
    const index = bundle.requests.indexOf(element, 0);
    bundle.requests.splice(index, 1)
    this.cartService.updateBundles(this.bundleList)
    this.calculateTotalPrice()
  }

  removeBundle(bundle: BundleAndVehicle) {
    const index = this.bundleList.indexOf(bundle, 0);
    this.bundleList.splice(index, 1)
    this.cartService.updateBundles(this.bundleList)
    this.calculateTotalPrice()
  }

  openPrices(vehicleId: number) {
    const dialogRef = this.dialog.open(ViewPriceListComponent, {
      width: '1200px',
      height: '600px',
      data: { id: vehicleId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDetails(vehicleId: number) {
    const dialogRef = this.dialog.open(VehicleDetailsComponent, {
      width: '1200px',
      height: '600px',
      data: { id: vehicleId }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  remove(request) {
    this.requests.forEach(element => {
      if (element.vehicleId == request.vehicleId) {
        this.requests.splice(this.requests.indexOf(element), 1);
        this.dataSourceRequests = new MatTableDataSource<RequestAndVehicle>(this.requests);
        this.cartService.updateRequests(this.requests)
        this.calculateTotalPrice()
        return;
      }
    });
  }

  buy() {
    this.cartService.buy().subscribe(data => {
      this._snackBar.open("Successfully rented!", "", {
        duration: 2000,
        verticalPosition: 'bottom'
      });
      this.cartService.newCart()
      this.ngOnInit()
    },
      error => {
        this._snackBar.open("Error occured", "", {
          duration: 2000,
          verticalPosition: 'bottom'
        });
      })
  }

  clear() {
    this.cartService.newCart()
    this.ngOnInit()
  }

  calculateTotalPrice() {
    this.calculateRequestPrice(this.requests)

    for (let b of this.bundleList) {
      this.calculateRequestPrice(b.requests)
    }
  }

  calculateRequestPrice(requests) {
    this.price=0
    for (let r of requests) {
      var requestPrice=0
      var startDate = r.startDate
      var endDate = r.endDate
      var prices = []
      this.pricelistService.getPricelists(r.vehicleId).subscribe(data => {
        prices = data
        console.log(prices)
        for (let p of prices) {
          console.log(startDate, endDate, p.startDate, p.endDate)
          console.log(p.price)
          console.log(this.daysDiff(endDate, startDate))
          if (this.compareDate(p.startDate, startDate) == -1 && this.compareDate(p.endDate, endDate) == 1) {
            this.price += p.price * (this.daysDiff(endDate, startDate))
            requestPrice  += p.price * (this.daysDiff(endDate, startDate))
          }
          else if (this.compareDate(p.startDate, startDate) == -1 && this.compareDate(p.endDate, endDate) == -1) {
            this.price += p.price * (this.daysDiff(p.endDate, startDate))
            requestPrice += p.price * (this.daysDiff(p.endDate, startDate))
          }
          else if (this.compareDate(p.startDate, startDate) == 1 && this.compareDate(p.endDate, endDate) == -1) {
            this.price += p.price * (this.daysDiff(endDate, p.startDate))
            requestPrice += p.price * (this.daysDiff(endDate, p.startDate))
          }
        }
        r.price=requestPrice
      });
    }

  }

  compareDate(date1: Date, date2: Date): number {
    let d1 = new Date(date1); let d2 = new Date(date2);

    // // Check if the dates are equal
    // let same = d1.getTime() === d2.getTime();
    // if (same) return 0;

    // Check if the first is greater than second
    if (d1 >= d2) return 1;

    // Check if the first is less than second
    if (d1 <= d2) return -1;
  }

  daysDiff(date1, date2) {

    var diff = Math.abs(new Date(date1).getTime() - new Date(date2).getTime());
    var diffDays = Math.ceil(diff / (1000 * 3600 * 24));
    return diffDays

  }

}
