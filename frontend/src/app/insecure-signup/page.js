"use client"; // This must be at the top of the file

import React, { useState } from "react";

const InsecureSignupForm = () => {
  const [userEmail, setUserEmail] = useState("");
  const [userName, setUserName] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [userSurname, setUserSurname] = useState("");
  const [role, setRole] = useState("");
  const [message, setMessage] = useState("");

  const handleSignup = async (e) => {
    e.preventDefault(); // Prevent form submission

    try {
      const URL = process.env.NEXT_PUBLIC_API_URL;
      console.log(`${URL}/insecure-signup?user_email=${userEmail}&user_name=${userName}&user_password=${userPassword}&user_surname=${userSurname}&role=${role}`);

      // Use the fetch API
      const response = await fetch(`${URL}/insecure-signup?user_email=${userEmail}&user_name=${userName}&user_password=${userPassword}&user_surname=${userSurname}&role=${role}`, {
        method: "POST",
      });

      if (response.ok) {
        const data = await response.text(); // Parse response as text
        setMessage(data); // Set the success message
      } else {
        setMessage("Error occurred during signup");
      }
    } catch (error) {
      setMessage("Error: " + error.message);
    }
  };

  return (
    <div>
      <h2>Insecure Signup Form</h2>
      <form onSubmit={handleSignup}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={userEmail}
            onChange={(e) => setUserEmail(e.target.value)}
          />
        </div>
        <div>
          <label>Username:</label>
          <input
            type="text"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={userPassword}
            onChange={(e) => setUserPassword(e.target.value)}
          />
        </div>
        <div>
          <label>Surname:</label>
          <input
            type="text"
            value={userSurname}
            onChange={(e) => setUserSurname(e.target.value)}
          />
        </div>
        <div>
          <label>Role:</label>
          <input
            type="text"
            value={role}
            onChange={(e) => setRole(e.target.value)}
          />
        </div>
        <button type="submit">Signup</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default InsecureSignupForm;
