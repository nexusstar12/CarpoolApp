import React, { useState } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography } from "@mui/material";

export const SearchResult = ({ result }) => {
  console.log("result", result);
  const [disabledButtons, setDisabledButtons] = useState([]);

  const handleJoinPoolClick = (poolId) => {
    setDisabledButtons((prevState) => {
      const newState = [...prevState];
      if (newState[poolId]) {
        newState[poolId] = false;
      } else {
        newState[poolId] = true;
      }
      return newState;
    });
  };

  const CardContainer = styled("div")`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
  `;

  const StyledCard = styled(Card)`
    flex: 0 0 calc(50% - 32px);
    margin: 16px;
    padding: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    border-radius: 10px;
    transition: 0.3s;
    &:hover {
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
    }
  `;

  const StyledCardContent = styled(CardContent)`
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  `;

  const JoinButton = styled(Button)`
    background-color: #4caf50;
    color: white;
    margin-top: 12px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
    &:hover {
      background-color: #45a049;
    }
  `;

  const CardTitle = styled("div")`
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 10px;
  `;

  const TimeContainer = styled("div")`
    display: flex;
    justify-content: space-between;
    margin-bottom: 3px;
    font-size: 0.9em;
  `;

  const DetailsContainer = styled("div")`
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const getCards = () => {
    return result.map((data) => (
      <StyledCard key={data.poolId}>
        <StyledCardContent>
          {/* Title */}
          <CardTitle>{data.description || "No Description"}</CardTitle>

          {/* Time */}
          <TimeContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Starts: </strong> {data.startTime || "N/A"}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              <strong>Ends: </strong> {data.endTime || "N/A"}
            </Typography>
          </TimeContainer>

          {/* Details */}
          <DetailsContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Starting Location: </strong> {data.startLocation}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              <strong>Ending Location: </strong> {data.endLocation}
            </Typography>
          </DetailsContainer>

          {/* Button */}
          <JoinButton
            variant="contained"
            onClick={() => handleJoinPoolClick(data.poolId)}
            style={{
              backgroundColor: disabledButtons[data.poolId] ? "red" : "green",
            }}
          >
            {disabledButtons[data.poolId] ? "LEAVE POOL" : "JOIN POOL"}
          </JoinButton>
        </StyledCardContent>
      </StyledCard>
    ));
  };

  return (
    <>
      <CardContainer>{getCards()}</CardContainer>

      <div>
        {result.length === 0 ? (
          <p>No results found.</p>
        ) : (
          <CardContainer>{getCards()}</CardContainer>
        )}
      </div>
    </>
  );
};
