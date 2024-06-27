import React, { useEffect } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import "./App.css";
import Login from "./components/Login";
import SignUp from "./components/signup_component";
import UserDetails from "./components/userDetails";
import AdminHome from "./components/adminHome";
import ProtectedRoute from "./components/ProtectedRoute";
import { useUserRole } from "./hooks/useUserRole";

function App() {
  const isLoggedIn = window.localStorage.getItem("loggedIn");
  const userRole = useUserRole();
  const userType = window.localStorage.getItem("userRole");
  const isAdmin = userRole === "ADMIN";

  useEffect(() => {}, []);

  return (
    <Router>
      <div className="App">

        <Routes>
          {!isLoggedIn && (
            <>
              <Route path="/register" element={<SignUp />} />
              <Route path="/" element={<Login />} />
            </>
          )}

          {/* ProtectedRoutes */}
          <Route element={<ProtectedRoute />}>
            <Route path="/login" element={<Navigate to="/" />} />
            <Route path="/register" element={<Navigate to="/" />} />
            <Route
              path="/"
              element={
                isAdmin ? (
                  <Navigate to="/admin-dashboard" />
                ) : (
                  <Navigate to="/user-details" />
                )
              }
            />
            <Route path="/admin-dashboard" element={<AdminHome />} />
            <Route
              path="/user-details"
              element={isAdmin ? <Navigate to="/" /> : <UserDetails />}
            />
          </Route>

          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
