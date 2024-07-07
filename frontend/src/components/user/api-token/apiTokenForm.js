import React, {useState} from 'react';
import "./style.css"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faChain, faKey, faUser} from "@fortawesome/free-solid-svg-icons";

const FormComponent = ({onClose, onSubmit}) => {
    const [name, setName] = useState('');
    const [expireDate, setExpireDate] = useState('');

    const handleSubmit = (e) => {
        if (!name || !expireDate) {
            alert("Please enter all fields!");
            return;
        } else if (new Date(expireDate) <= new Date()) {
            alert("Your expiration date has past!");
            return;
        }
        e.preventDefault();
        onSubmit({name, expireDate});
        onClose();
    };

    return (
        <div className="screen-3">
            <div className="row-inputs">
                <div className="email">
                    <label htmlFor="token-name">Token Name</label>
                    <div className="sec-2">
                        <FontAwesomeIcon
                            icon={faChain}
                        />
                        <input name="token-name"
                               onChange={(e) => setName(e.target.value)} required={true}/>
                    </div>
                </div>
                <div className="date">
                    <label htmlFor="expiration-date">Expiration Date</label>
                    <div className="sec-2">
                        <input name="expiration-date" type="date" value={expireDate}
                               onChange={(e) => setExpireDate(e.target.value)} required={true}/>
                    </div>
                </div>
            </div>
            <div className="footer">
                <button className="login" onClick={handleSubmit}>Submit</button>
                <button className="login" onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
};

export default FormComponent;
