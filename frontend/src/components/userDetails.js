import React, { useEffect, useState } from "react";
import { faTrash} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useNavigate } from "react-router-dom";
import "../App.css";
import FormComponent from './apiTokenForm';

export default function AdminHome({}) {
  //setting state
  const [data, setData] = useState([]);
  const [searchQuery,setSearchQuery]=useState("")
  const [isFormOpen, setIsFormOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    getAllApiTokens();
  },[searchQuery]);

  //fetching all tokens
  const getAllApiTokens = () => {
    fetch('http://localhost:8080/user/api-tokens?pageNum=0&pageSize=100', {
      method: "GET",
      headers: {'Authorization': window.localStorage.getItem("Token")}
    })
      .then((res) => res.json())
      .then((data) => {
        setData(data.tokens);
      }).catch((error) => {  


      });;
  };

  const handleButtonClick = () => {
    setIsFormOpen(true);
  };

  const handleCloseForm = () => {
    setIsFormOpen(false);
  };
  
  //logout
  const logOut = () => {
    window.localStorage.clear();
    navigate("/");
  };

  //deleting user
  const deleteApiToken = (name) => {
    if (window.confirm(`Are you sure you want to delete token ${name}`)) {
      var apiKey
      for (let token of data) {
        if (token.name == name){
          apiKey = token.api_token
        }
      }
      fetch("http://localhost:8080/user/api-tokens", {
        method: "DELETE",
        headers: {
          'X-API-Key': apiKey,
        }

      })
        .then((res) => res.json())
        .then((data) => {
          getAllApiTokens();
        });
    } else {
    }
  };

  const createApiToken = (formData) => {
    var expire_date = formData.expire_date += "T23:59:59Z";
    var name = formData.name;
    fetch('http://localhost:8080/user/api-tokens', {
      method: "POST",
      headers: {'Authorization': window.localStorage.getItem("Token"), 'Content-Type': 'application/json'},
      body: JSON.stringify({
        name,
        expire_date
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        alert('API token created!')
        getAllApiTokens();
      }).catch((error) => {
        alert('There is a problem. please contact to admin!')
      });;
  };


  return (
    <div className="auth-wrapper" style={{ height: "auto", marginTop: 50 }}>
      <div className="auth-inner" style={{ width: "fit-content" }}>
        <h3>Welcome User</h3>
        <div style={{ position: "relative", marginBottom: 10 }}>
          <span
            style={{ position: "absolute", right: 10, top: 8, color: "#aaa" }}
          >
          </span>
        </div>
        <div>
          <button onClick={handleButtonClick}>create new API token</button>
          {isFormOpen && (
            <FormComponent onClose={handleCloseForm} onSubmit={createApiToken} />
          )}
        </div>
        <table style={{ width: 700 }}>
          <tr style={{ textAlign: "center" }}>
            <th>Token Name</th>
            <th>Expiretion Date</th>
            <th>Detele</th>
          </tr>
          {data.map((i) => {
            return (
              <tr style={{ textAlign: "center" }}>
                <td>{i.name}</td>
                <td>{i.expire_date}</td>
                <td>
                  <FontAwesomeIcon
                    icon={faTrash}
                    onClick={() => deleteApiToken(i.name)}
                  />
                </td>
              </tr>
            );
          })}
        </table>

        <button
          onClick={logOut}
          className="btn btn-primary"
          style={{ marginTop: 10 }}
        >
          Log Out
        </button>
      </div>
    </div>
  );
}