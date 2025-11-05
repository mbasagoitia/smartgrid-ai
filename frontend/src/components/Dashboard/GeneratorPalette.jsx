import React from "react";
import { useDrag } from "react-dnd";
import GENERATORS from "./Generators";

export default function GeneratorPalette({ budget, onPlace }) {
  return (
    <div style={{ display: "flex", gap: 10 }}>
      {GENERATORS.map((g) => {
        const affordable = budget >= g.cost;
        return (
          <Generator
            key={g.type}
            generator={g}
            canDrag={affordable}
            onPlace={onPlace}
          />
        );
      })}
    </div>
  );
}

function Generator({ generator, canDrag }) {
  const [{ isDragging }, dragRef] = useDrag({
    type: "GENERATOR",
    item: { ...generator },
    canDrag: canDrag,
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging()
    }),
  });

  return (
    <div
      className="text-center"
      ref={dragRef}
      style={{
        padding: 10,
        border: '1px solid #333',
        borderRadius: 4,
        cursor: canDrag ? 'grab' : 'not-allowed',
        opacity: isDragging ? 0.5 : canDrag ? 1 : 0.5
      }}
      title={canDrag ? `Drag to place ${generator.type}` : `Insufficient budget for ${generator.type}`}
    >
      {generator.icon} {generator.type} ({generator.cost})
    </div>
  );
}

