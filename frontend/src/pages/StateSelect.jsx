import React from "react";

export default function StateSelect({ onSelect }) {

  const handleChange = (e) => {
    const value = e.target.value;
    let stateObj = null;

    switch (value) {
      case "Colorado":
        stateObj = { name: "Colorado", abbr: "CO" };
        break;
      case "California":
        stateObj = { name: "California", abbr: "CA" };
        break;
      case "Texas":
        stateObj = { name: "Texas", abbr: "TX" };
        break;
      default:
        stateObj = null;
    }

    onSelect(stateObj);
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>SmartGrid Simulator</h1>
      <p>Select a state to simulate:</p>
      <select onChange={handleChange}>
        <option value="">--Select--</option>
        <option value="Colorado">Colorado</option>
        <option value="California">California</option>
        <option value="Texas">Texas</option>
      </select>
    </div>
  );
}
