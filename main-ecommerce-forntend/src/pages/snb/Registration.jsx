import { useState } from "react";
import { FiUser } from "react-icons/fi";
import { ToastContainer, toast } from "react-toastify";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css';

const Registration = () => {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "", // ✅ updated key
    password: "",
    confirmPassword: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prevForm) => ({ ...prevForm, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (form.password !== form.confirmPassword) {
      setMessage("Passwords do not match.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8000/user/signup", form);
      const message = response.data;

      if (message === "User registered successfully") {
        toast.success(message);
        setForm({
          firstName: "",
          lastName: "",
          email: "",
          phoneNumber: "",
          password: "",
          confirmPassword: "",
        });
        setMessage("");
        setTimeout(() => {
            navigate("/login");
        }, 2000);
      } else {
        toast.error(message);
        setMessage(message);
      }

    } catch (error) {
      toast.error("An error occurred during registration.");
      setMessage("An error occurred during registration.");
      console.error(error);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-white">
      <ToastContainer position="top-right" autoClose={2000} hideProgressBar />
      <div className="w-full max-w-xl bg-white border border-gray-200 shadow-2xl rounded-2xl p-10 relative">
        <div className="text-center mb-8">
          <div className="inline-flex items-center rounded-full bg-black px-4 py-2 text-sm font-semibold text-white shadow-lg">
            <FiUser className="mr-2" />
            Create Account
          </div>
        </div>

        {message && (
          <div className="mb-4 text-red-500 font-medium text-sm text-center">{message}</div>
        )}

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-6">
          {/* First Name */}
          <div className="col-span-1">
            <label htmlFor="firstName" className="block mb-2 text-sm font-semibold text-black">
              First Name
            </label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={form.firstName}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Last Name */}
          <div className="col-span-1">
            <label htmlFor="lastName" className="block mb-2 text-sm font-semibold text-black">
              Last Name
            </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={form.lastName}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Email */}
          <div className="col-span-2">
            <label htmlFor="email" className="block mb-2 text-sm font-semibold text-black">
              Email Address
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Phone Number */}
          <div className="col-span-2">
            <label htmlFor="phoneNumber" className="block mb-2 text-sm font-semibold text-black">
              Phone Number
            </label>
            <input
              type="tel"
              id="phoneNumber"
              name="phoneNumber"
              value={form.phoneNumber}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Password */}
          <div className="col-span-1">
            <label htmlFor="password" className="block mb-2 text-sm font-semibold text-black">
              Password
            </label>
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Confirm Password */}
          <div className="col-span-1">
            <label htmlFor="confirmPassword" className="block mb-2 text-sm font-semibold text-black">
              Confirm Password
            </label>
            <input
              type={showConfirmPassword ? "text" : "password"}
              id="confirmPassword"
              name="confirmPassword"
              value={form.confirmPassword}
              onChange={handleChange}
              className="w-full px-4 py-3 border rounded-xl border-gray-300 focus:ring-2 focus:ring-gray-300 outline-none"
              required
            />
          </div>

          {/* Submit */}
          <div className="col-span-2">
            <button
              type="submit"
              className="w-full bg-black text-white font-semibold py-3 rounded-xl hover:bg-gray-900 transition"
            >
              Create Account
            </button>
          </div>
        </form>

        <p className="text-center text-sm text-gray-600 mt-6">
          Already have an account?{" "}
          <a href="/login" className="text-blue-600 font-semibold hover:underline">
            Login here
          </a>
        </p>
      </div>
    </div>
  );
};

export default Registration;
