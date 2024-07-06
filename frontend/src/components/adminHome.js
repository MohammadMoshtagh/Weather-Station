import React, {useEffect, useState} from "react";
import {faBan, faBolt, faGavel} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useNavigate} from "react-router-dom";
import "../App.css";

export default function AdminHome() {
    //setting state
    const [data, setData] = useState([]);
    const [searchQuery] = useState("")
    const [toggleButtons, setToggleButtons] = useState({})
    const navigate = useNavigate();

    useEffect(() => {
        getAllUser();
    }, [searchQuery]);

    //fetching all user
    const getAllUser = () => {
        fetch('http://localhost:8000/admin/users?pageNum=0&pageSize=1000', {
            method: "GET",
            headers: {'Authorization': window.localStorage.getItem("Token")}
        })
            .then((res) => res.json())
            .then((data) => {
                let userToggles = {}
                data.users.sort((a, b) => {
                    if (a.username === "admin") {
                        return -1;
                    }
                    if (a.username < b.username) {
                        return -1;
                    } else {
                        return 1;
                    }
                });
                setData(data.users);
                for (let user of data.users) {
                    if (user.username === "admin") {
                        userToggles["admin"] = faGavel;
                        continue;
                    }
                    if (user.enable === "true") {
                        userToggles[user.username] = faBolt;
                    } else {
                        userToggles[user.username] = faBan;
                    }
                    setToggleButtons(userToggles);
                }
            }).catch((error) => {
            alert('There is a problem. please contact to admin!')
        });
    };

    //logout
    const logOut = () => {
        window.localStorage.clear();
        navigate("/");
    };

    //deleting user
    const changeUserStatus = (username) => {
        if (username === 'admin') return;
        if (window.confirm(`Are you sure you want to change status for ${username}`)) {
            var currenetStatus
            for (let user of data) {
                if (user.username === username) {
                    if (user.enable === 'true')
                        currenetStatus = true
                    else
                        currenetStatus = false
                }
            }
            fetch("http://localhost:8000/admin/users?username=" + username + "&active=" + !currenetStatus, {
                method: "PUT",
                headers: {'Authorization': window.localStorage.getItem("Token")}
            })
                .then((res) => res.json())
                .then((data) => {
                    getAllUser();
                });
        } else {
        }
    };

    return (
        <div className="auth-wrapper" style={{height: "auto", marginTop: 50}}>
            <div className="auth-inner" style={{width: "fit-content"}}>
                <h3>Welcome Admin</h3>
                <div style={{position: "relative", marginBottom: 10}}>
          <span
              style={{position: "absolute", right: 10, top: 8, color: "#aaa"}}
          >
          </span>
                </div>
                <table style={{width: 700}}>
                    <tbody>
                    <tr style={{textAlign: "center"}}>
                        <th>Username</th>
                        <th>Register Date</th>
                        <th>Change Status</th>
                    </tr>
                    </tbody>
                    {data.map((i) => {
                        return (
                            <tr style={{textAlign: "center"}}>
                                <td>{i.username}</td>
                                <td>{i.createDate}</td>
                                <td>
                                    <FontAwesomeIcon
                                        icon={toggleButtons[i.username]}
                                        onClick={() => changeUserStatus(i.username)}
                                    />
                                </td>
                            </tr>
                        );
                    })}
                </table>

                <button
                    onClick={logOut}
                    className="btn btn-primary"
                    style={{marginTop: 10}}
                >
                    Log Out
                </button>
            </div>
        </div>
    );
}