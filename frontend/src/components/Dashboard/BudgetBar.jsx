import React from "react";
import { useSimulation } from "../../hooks/useSimulation";

export default function BudgetBar() {
  const { budget } = useSimulation();

  return (
    <div className="flex justify-between items-center">
        <h3 className="font-semibold mb-4">State Budget</h3>
        <div>
            <p className="text-gray-600">{budget.toLocaleString()} credits remaining</p>
        </div>
    </div>
  );
}
