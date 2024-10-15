import { CheckoutItemPostVm } from "./CheckoutItemPostVm";

export interface CheckoutPostVm {
  email: string;
  note?: string; // Optional property
  checkoutItemPostVms: CheckoutItemPostVm[];
}
