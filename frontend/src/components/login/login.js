import React, {useState} from "react";
import "../../index.css";
import "./style.css";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faKey, faUser} from "@fortawesome/free-solid-svg-icons";

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

                if (data.role === "ADMIN") {
                    navigate("/admin-dashboard");
                } else {
                    navigate("/user-details");
                }
            })
            .catch((error) => {
                alert("Invalid username or password.");
                setPassword("")
            });
    }

    return (

    <div className="auth-wrapper">
        <div className="auth-inner">
            <div className="screen-1">
                <div className="email">
                    <label htmlFor="email">Username</label>
                    <div className="sec-2">
                        <FontAwesomeIcon
                            icon={faUser}
                        />
                        <input type="email" name="email" placeholder=""
                               onChange={(e) => setEmail(e.target.value)} />
                    </div>
                </div>
                <div className="password">
                    <label htmlFor="password">Password</label>
                    <div className="sec-2">
                        <FontAwesomeIcon
                                         icon={faKey}
                        />
                        <input id="pas" className="pas" value={password} type="password" name="password" placeholder=""
                               onChange={(e) => setPassword(e.target.value)}/>
                    </div>
                </div>
                <button className="login" onClick={handleSubmit}>Login</button>
                <div className="footer"><a href="/register"><span>Signup</span></a></div>
            </div>
        </div>
    </div>
    )
        ;
}
