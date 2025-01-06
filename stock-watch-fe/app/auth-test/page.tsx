import { getServerSession } from "next-auth";
import { authOptions } from "@/lib/next-auth-options";
import { SignIn, SignOut } from "@/components/next-auth-buttons";

export default async function Home() {
  const session = await getServerSession(authOptions);
  return (
    <main className="flex flex-col gap-4 p-4 mx-auto">
      {!!session && <pre>{JSON.stringify(session, null, 2)}</pre>}
      {!!session ? <SignOut /> : <SignIn />}
    </main>
  );
}
