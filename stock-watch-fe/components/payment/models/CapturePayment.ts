export interface CapturePayment {
  orderId: number;
  checkOutId: string;
  amount: number; // Or 'BigDecimal' if using a BigDecimal library
  paymentMethod: string; // Define this enum separately
  paymentStatus: string; // Define this enum separately
  failureMessage: string;
}
