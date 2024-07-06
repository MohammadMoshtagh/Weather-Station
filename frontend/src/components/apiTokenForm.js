import React, {useState} from 'react';

const FormComponent = ({onClose, onSubmit}) => {
    const [name, setName] = useState('');
    const [expire_date, setDate] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({name, expire_date});
        onClose();
    };

    return (
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '10px'}}>
                <label>
                    Name:
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required/>
                </label>
                <label>
                    Date:
                    <input type="date" value={expire_date} onChange={(e) => setDate(e.target.value)} required/>
                </label>
                <button type="submit">Submit</button>
                <button type="button" onClick={onClose}>Cancel</button>
            </form>
        </div>
    );
};

export default FormComponent;
