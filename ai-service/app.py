from fastapi import FastAPI
from pydantic import BaseModel
import os
from openai import OpenAI

app = FastAPI()
client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

class GridData(BaseModel):
    timestamp: str | None = None
    generation: list[float]
    consumption: list[float]
    storage: list[float]

@app.post("/forecast")
def forecast(data: GridData):
    prompt = f"""
    Given this smart grid state:
    Generation: {data.generation}
    Consumption: {data.consumption}
    Storage: {data.storage}

    Predict next hour's demand and suggest an action.
    """
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": prompt}],
    )
    result = response.choices[0].message.content
    return {"recommendation": result}
