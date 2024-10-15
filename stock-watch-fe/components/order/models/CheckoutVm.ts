import { CheckoutItemVm } from "./CheckoutItemVm";

export interface CheckoutVm {
  id: string;
  email: string;
  note: string;
  checkoutState: string;
  checkoutItemSet: Set<CheckoutItemVm>; // Using Set for unique items
}
