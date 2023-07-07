import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NavigationService} from "../navigation.service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  // @ts-ignore
  category: string;

  public constructor(private navService: NavigationService, private router: Router) {
  }

  // basic navigation functions
  goToGeneral() {
    this.category= "general";
    this.navService.valueTransfer(this.category)
  }
  goToBusiness() {
    this.category= "business";
    this.navService.valueTransfer(this.category)
  }
  goToEntertainment() {
    this.category= "entertainment";
    this.navService.valueTransfer(this.category)
  }
  goToHealth() {
    this.category= "health";
    this.navService.valueTransfer(this.category)
  }
  goToScience() {
    this.category= "science";
    this.navService.valueTransfer(this.category)
  }
  goToSports() {
    this.category= "sports";
    this.navService.valueTransfer(this.category)
  }
  goToTech() {
    this.category= "technology";
    this.navService.valueTransfer(this.category)
  }

  goToSearch() {
    this.router.navigateByUrl('/results');
  }

}
