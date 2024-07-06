import React, {useState} from "react";
import "../../index.css";
import "./style.css";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faKey, faUser} from "@fortawesome/free-solid-svg-icons";

export default function SignUp() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const requestBody = JSON.stringify({
            username,
            password,
        });

        fetch("http://localhost:8000/users/register", {
            method: "POST",
            headers: {"Content-Type": "application/json", Accept: "application/json"},
            body: requestBody
        })
            .then((res) => {

            })
            .then((data) => {
                alert("Registration Successful. contact with admin for activation.");
                navigate('/');
            })
            .catch((error) => {
                alert("Try with another username");
            });


    };

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
                                   onChange={(e) => setUsername(e.target.value)}/>
                        </div>
                    </div>
                    <div className="password">
                        <label htmlFor="password">Password</label>
                        <div className="sec-2">
                            <FontAwesomeIcon
                                icon={faKey}
                            />
                            <input id="pas" className="pas" value={password} type="password" name="password"
                                   placeholder=""
                                   onChange={(e) => setPassword(e.target.value)}/>
                        </div>
                    </div>
                    <button className="signup" onClick={handleSubmit}>Signup</button>
                    <div className="footer">Already registered? <a href="/public"><span>Login</span></a></div>
                </div>
            </div>
        </div>
    );
}