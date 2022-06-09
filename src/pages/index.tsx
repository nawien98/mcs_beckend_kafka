import type { NextPage } from "next";
import Head from "next/head";
import LoginPage from "./login/Login";

const Home: NextPage = () => {
 
  return (
    <>
      <Head>
        <title>Accolite Digital | Accelerators </title>
        <meta
          name="description"
          content="Accolite Digital offers a wide range of technology services. "
        />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <div>MicroServices Accelerator</div>   
    </>
  );
};

export default Home;
