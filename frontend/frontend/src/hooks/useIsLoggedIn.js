import { useState, useEffect } from "react";
export const useIsLoggedIn = () => {
    const userRole = localStorage.getItem('userRole');
    const [role, setRole] = useState(userRole);
  
    const handleIsLoggedIn = () => {
      const userRole = localStorage.getItem('userRole');
      setRole(userRole);
    };
  
    useEffect(() => {
        window.addEventListener('storage', handleIsLoggedIn, false);
        handleIsLoggedIn();
    }, []);
  
    return role;
  };