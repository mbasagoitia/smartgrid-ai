import React, { useState, useRef, useEffect } from "react";
import "../../styles/components/Instructions.css";

export default function Instructions() {
  const [open, setOpen] = useState(true);
  const contentRef = useRef(null);
  const [height, setHeight] = useState("auto");

  useEffect(() => {
    const el = contentRef.current;
    if (!el) return;
    if (open) {
      setHeight(`${el.scrollHeight}px`);
      const t = setTimeout(() => setHeight("auto"), 300);
      return () => clearTimeout(t);
    } else {
      setHeight(`${el.scrollHeight}px`);
      requestAnimationFrame(() => setHeight("0px"));
    }
  }, [open]);

  return (
    <div className="instructions">
      <button
        className="instructions-toggle"
        onClick={() => setOpen((s) => !s)}
        aria-expanded={open}
        aria-controls="instructions-content"
      >
        <span className="instructions-title">Instructions <span className="expand-collapse-text">(expand/collapse)</span></span>
        <svg
          className={`chevron ${open ? "chevron-open" : ""}`}
          width="18"
          height="18"
          viewBox="0 0 24 24"
          aria-hidden="true"
        >
          <path fill="currentColor" d="M8.59 16.59L13.17 12 8.59 7.41 10 6l6 6-6 6z" />
        </svg>
      </button>

      <div
        id="instructions-content"
        ref={contentRef}
        className="instructions-content"
        style={{ maxHeight: height }}
      >
        <div className="instructions-body">
          <ul className="instructions-list">
            <li>Devise a strategy to power the entire state for one year with clean energy.</li>
            <li>Consider regional power demand, generator cost, efficiency, weather patterns, and transmission loss between generators.</li>
            <li>Drag and drop generators onto counties on the map.</li>
            <li>Each generator type costs part of your state energy budget.</li>
            <li>Choose a battery storage/usage strategy (conservative, aggressive, or predictive).</li>
            <li>Click “Start Simulation” to see results.</li>
          </ul>
        </div>
      </div>
    </div>
  );
}
