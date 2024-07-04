import React, { Component, useState } from "react";
import "../index.css";
import { useNavigate } from "react-router-dom";
export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  function handleSubmit(e) {
    e.preventDefault();
    const requestBody = JSON.stringify({
      username: email,
      password,
    });
    fetch("http://localhost:8000/users/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: requestBody,
    })
      .then((res) => {
        window.localStorage.setItem("Token", res.headers.get("Authorization"));
        const jsonResponse = res.json();
        return jsonResponse;
      })
      .then((data) => {
        window.localStorage.setItem("userRole", data.role);
        window.localStorage.setItem("username", data.username);
        window.localStorage.setItem("loggedIn", true);

        if (data.role == "ADMIN") {
          navigate("/admin-dashboard");
        } else {
          navigate("/user-details");
        }
      })
      .catch((error) => {
          alert("Invalid username or password.");
      });
  }

  return (
    <div className="auth-wrapper">
      <div className="auth-inner">
        <form onSubmit={handleSubmit}>
          <h3>Login</h3>

          <div className="mb-3">
            <label>Username</label>
            <input
              className="form-control"
              placeholder="Enter username"
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className="d-grid">
            <button type="submit" className="btn btn-primary">
              Submit
            </button>
          </div>
          <p className="forgot-password text-right">
            <a href="/register">Register</a>
          </p>
        </form>
      </div>
    </div>
  );
}
