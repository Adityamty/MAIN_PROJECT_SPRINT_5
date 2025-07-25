import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import ClipLoader from "react-spinners/ClipLoader";
import { FiMail, FiLock, FiEye, FiEyeOff, FiShield } from "react-icons/fi";
import 'react-toastify/dist/ReactToastify.css';

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Please enter a valid email address.");
      setLoading(false);
      return;
    }

    if (password.length < 6) {
      setError("Password must be at least 6 characters long.");
      setLoading(false);
      return;
    }

    try {
      const response = await axios.post("http://localhost:8000/user/login", {
        email,
        password,
      });

      if (response.status === 200 && response.data.userId) {
        const { userId, fullName, email } = response.data;
        sessionStorage.setItem("userId", userId);
        sessionStorage.setItem("userName", fullName);
        sessionStorage.setItem("userEmail", email);
        toast.success("Login successful!");
        setTimeout(() => navigate("/"), 1500);
      } else {
        toast.error("Login failed: " + response.data.message);
      }
    } catch (err) {
      console.error(err);
      toast.error("Invalid email or password.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-white">
      <ToastContainer position="top-right" autoClose={2000} hideProgressBar />

      {/* Background pattern layer */}
      <div className="bg-grid-pattern absolute inset-0 opacity-5"></div>

      <div className="relative w-full max-w-md">
        
        <div className="rounded-2xl border border-gray-200 bg-white p-8 shadow-2xl">
          {/* Avatar icon */}
          <div className="mb-8 text-center">
            
            <h1 className="mb-2 text-3xl font-bold text-black">Login</h1>
            <p className="text-gray-700">Please enter your credentials</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="space-y-2">
              <label htmlFor="email" className="block text-sm font-semibold text-black">Email Address</label>
              <div className="relative">
            
                <input
                  type="email"
                  id="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full rounded-xl border border-gray-300 bg-white py-3 pr-4 pl-10 transition-all duration-200 hover:border-gray-400 focus:ring-2 focus:ring-gray-300 focus:outline-none"
                  placeholder="Enter your email"
                  required
                  disabled={loading}
                />
              </div>
            </div>

            <div className="space-y-2">
              <label htmlFor="password" className="block text-sm font-semibold text-black">Password</label>
              <div className="relative">

                <input
                  type={showPassword ? 'text' : 'password'}
                  id="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full rounded-xl border border-gray-300 bg-white py-3 pr-4 pl-10 transition-all duration-200 hover:border-gray-400 focus:ring-2 focus:ring-gray-300 focus:outline-none"
                  placeholder="Enter your password"
                  required
                  disabled={loading}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute top-1/2 right-3 -translate-y-1/2 transform cursor-pointer text-gray-500 hover:text-black"
                  disabled={loading}
                >
                  
                </button>
              </div>
            </div>

            {error && (
              <div className="rounded-xl border border-gray-300 bg-gray-100 p-3">
                <p className="text-sm font-medium text-black">{error}</p>
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full transform cursor-pointer rounded-xl bg-black px-4 py-3 font-semibold text-white shadow-lg transition-all duration-200 hover:scale-[1.02] hover:bg-gray-800 disabled:opacity-50"
            >
              {loading ? (
                <div className="flex items-center justify-center">
                  <ClipLoader color="white" size={20} />
                  <span className="ml-2">Logging in...</span>
                </div>
              ) : (
                'Login to Account'
              )}
            </button>

            <p className="text-center text-sm text-gray-700 mt-4">
              Don’t have an account?{" "}
              <Link
                to="/signup"
                className="font-semibold text-blue-500 hover:underline"
              >
                Sign up
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
