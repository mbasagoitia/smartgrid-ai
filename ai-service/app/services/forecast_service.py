from app.schemas.forecast_request import ForecastRequest
from app.schemas.forecast_response import ForecastResponse

class ForecastService:
    def predict(self, data: ForecastRequest) -> ForecastResponse:
        # Replace with the ai model's analysis and suggestion
        predicted_output = ""
        recommended_placement = ""

        return ForecastResponse(
            predicted_output_mw=predicted_output,
            recommended_placement=recommended_placement
        )
