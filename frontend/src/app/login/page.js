"use client"; // This must be at the top of the file

import React, { useState } from "react";
import { useRouter } from "next/navigation";

const Login = () => {
  const router = useRouter();

  // Initial state for form fields
  const [formData, setFormData] = useState({
    userEmail: "",
    userPassword: "",
  });

  // State for error handling
  const [error, setError] = useState("");

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
      // Make a POST request to the backend
      const API_URL = process.env.NEXT_PUBLIC_API_URL;
      const response = await fetch(`${API_URL}/login`, {
        method: "POST", // Use POST for login
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userEmail: formData.userEmail, // Correct property names for email
          userPassword: formData.userPassword, // Correct property names for password
        }),// Send the form data
      });

      // Check the response from the backend
      if (response.ok) {
        const user = await response.json(); // Assuming the backend returns user details

        setFormData({
          userEmail: "",
          userPassword: "",
        });

        alert("You are logged in"); 

        if (user.role === "admin") {
          // Redirect to admin dashboard
          router.push("/admin");
      } else {
          // Redirect to regular user page
          router.push("/user");
      }
      } else {
        // Handle error response
        const errorMessage = await response.text();
        setError(errorMessage || "Invalid credentials. Please try again.");
      }
    } catch (error) {
      console.error("Network error:", error);
      setError("An error occurred. Please try again later.");
    }
  };

  // Back button click handler
  const handleBackClick = () => {
    router.back(); // Go back to the previous page
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <h1 className="text-2xl font-semibold mb-6">Log In</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-sm bg-white p-6 rounded-lg shadow-md">
        {/* Error Message */}
        {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

        {/* Email */}
        <label className="input input-bordered flex items-center gap-2 mb-4">
          <input
            type="email"
            name="userEmail"
            value={formData.userEmail}
            onChange={handleInputChange}
            className="grow"
            placeholder="Email"
            required
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
            required
          />
        </label>

        {/* Submit Button */}
        <button
          type="submit"
          className="btn btn-primary w-full mb-2"
        >
          Connection
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

export default Login;
