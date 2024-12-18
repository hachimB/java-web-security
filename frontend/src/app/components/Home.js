
"use client"
import React from 'react';
import "./Home.css";
import { useRouter } from 'next/navigation';

const Home = () => {
  const router = useRouter();

  const handleLoginClick = () => {
    router.push('login');
  }

  const handleSigninClick = () => {
    router.push('signin');
  }


  return (
    <div>

       <div className="main-div">
        <div className="Text-div">
          <h1>Welcome</h1>
        </div>

        <div className="button-class ">
          <button className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg btn1" onClick={handleSigninClick}>Signup</button>
          <button className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg btn2" onClick={handleLoginClick}>Login</button>
        </div>
      </div>

    </div>
  )
}

export default Home