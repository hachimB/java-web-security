"use client"; // This must be at the top of the file

import React, { useState } from "react";
import { useRouter } from "next/navigation";

const Signin = () => {
  const router = useRouter();

  // Initial state for form fields
  const [formData, setFormData] = useState({
    userName: "",
    userSurname: "",
    userEmail: "",
    userPassword: "",
  });

  // Input change handler to update form state
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Form submission handler
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission

    try {
        const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/signup`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                userName: formData.userName,
                userSurname: formData.userSurname,
                userEmail: formData.userEmail,
                userPassword: formData.userPassword,
            }),
        });

        if (response.ok) {
            alert("You have been registered");

            // Clear the form fields
            setFormData({
                userName: "",
                userSurname: "",
                userEmail: "",
                userPassword: "",
            });

            router.back();
        } else {
            alert("Error submitting form.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
  };


  // Back button click handler
  const handleBackClick = () => {
    router.back(); // Go back to the previous page
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <h1 className="text-2xl font-semibold mb-6">Sign Up</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-sm bg-white p-6 rounded-lg shadow-md">
        {/* Username */}
        <label className="input input-bordered flex items-center gap-2 mb-4">
          <input
            type="text"
            name="userName"
            value={formData.userName}
            onChange={handleInputChange}
            className="grow"
            placeholder="Username"
          />
        </label>

        {/* Surname */}
        <label className="input input-bordered flex items-center gap-2 mb-4">
          <input
            type="text"
            name="userSurname"
            value={formData.userSurname}
            onChange={handleInputChange}
            className="grow"
            placeholder="Surname"
          />
        </label>

        {/* Email */}
        <label className="input input-bordered flex items-center gap-2 mb-4">
          <input
            type="email"
            name="userEmail"
            value={formData.userEmail}
            onChange={handleInputChange}
            className="grow"
            placeholder="Email"
          />
        </label>

        {/* Password */}
        <label className="input input-bordered flex items-center gap-2 mb-4">
          <input
            type="password"
            name="userPassword"
            value={formData.userPassword}
            onChange={handleInputChange}
            className="grow"
            placeholder="Password"
          />
        </label>

        {/* Submit Button */}
        <button
          type="submit"
          className="btn btn-primary w-full mb-2"
        >
          Submit
        </button>

        {/* Back Button */}
        <button
          type="button"
          className="btn btn-secondary w-full"
          onClick={handleBackClick}
        >
          Back
        </button>
      </form>
    </div>
  );
};

export default Signin;