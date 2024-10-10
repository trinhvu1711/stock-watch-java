import { authOptions } from "@/lib/next-auth-options";
import NextAuth from "next-auth/next";

// Create NextAuth handler using the defined authentication options
const handler = NextAuth(authOptions);

// Export the handler for both GET and POST requests
export { handler as GET, handler as POST };
