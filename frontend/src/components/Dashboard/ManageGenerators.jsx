import React from "react";
import "../../styles/components/ManageGenerators.css";

export default function ManageGenerators({ onReset, onUndo, disableUndo }) {
  return (
    <div className="manage-generators">
      <button className="undo-btn" onClick={onUndo} disabled={disableUndo}>
        Undo Last
      </button>
      <button className="reset-btn" onClick={onReset}>
        Clear All
      </button>
    </div>
  );
}
