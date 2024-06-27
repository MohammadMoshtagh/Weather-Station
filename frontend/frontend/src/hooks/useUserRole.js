import { useState, useEffect } from "react";
export const useUserRole = () => {
    const userRole = localStorage.getItem('userRole');
    const [role, setRole] = useState(userRole);
  
    const handleUserRole = () => {
      const userRole = localStorage.getItem('userRole');
      setRole(userRole);
    };
  
    useEffect(() => {
        window.addEventListener('storage', handleUserRole, false);
        handleUserRole();
    }, []);
  
    return role;
  };