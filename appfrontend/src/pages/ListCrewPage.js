import React, { useState, useEffect, useContext } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography, Box } from "@mui/material";
import axiosInstance from "../config/axios.config";
import { UserContext } from "../App";
import { LoadingBackdrop } from "../components/LoadingData";
import { useNavigate } from "react-router-dom";

export default function ListCrewPage() {
  const history = useNavigate();
  const [crews, setCrews] = useState([]);
  const userContext = useContext(UserContext);
  const { profileId, jwtToken } = userContext.userInfo;
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const { data } = await axiosInstance.get(`/crew/${profileId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });
        setCrews(data);
        setIsLoading(false);
      } catch (error) {
        console.log("error", error);
        setError(error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleClick = async (crewId) => {
    const requestBody = {
      profileId,
      crew_id: crewId,
    };

    try {
      await axiosInstance.delete(`/crew/remove/member`, {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
        data: requestBody,
      });
      setIsLoading(true);
      const { data } = await axiosInstance.get(`/crew/${profileId}`, {
        headers: {
          Authorization: `Bearer ${jwtToken}`,
        },
      });
      setCrews(data);
      setIsLoading(false);
    } catch (error) {
      if (error?.response?.status >= 500) {
        history("/down");
      }
    }
  };

  const CardContainer = styled("div")`
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
  `;

  const StyledCard = styled(Card)`
    flex: 0 0 calc(50% - 40px);
    margin: 25px;
    padding: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    border-radius: 15px;
    transition: 0.3s;
    &:hover {
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
    }
  `;

  const StyledCardContent = styled(CardContent)`
    padding: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center; // This will horizontally center-align the child elements
  `;

  const JoinButton = styled(Button)`
    background-color: #4caf50;
    color: white;
    margin-top: 12px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
    width: 150px; // Set a fixed width for the button
    border-radius: 25px; // Increase border-radius for a more rounded appearance
    &:hover {
      background-color: #45a049;
    }
  `;

  const CardTitle = styled("div")`
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 10px;
  `;

  const DetailsContainer = styled("div")`
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const getCards = (data) => {
    if (!data) {
      return (
        <StyledCard>
          <StyledCardContent>
            <DetailsContainer>
              <Typography variant="body2" color="textSecondary">
                <strong>Current Crew: </strong>
              </Typography>
            </DetailsContainer>
          </StyledCardContent>
        </StyledCard>
      );
    }
    return data.map((dataRow) => (
      <StyledCard key={dataRow.crewId}>
        <StyledCardContent>
          {/* Title */}
          <CardTitle>{dataRow?.description}</CardTitle>

          {dataRow.members.map((member) => {
            return (
              <CardContainer>
                <Typography variant="body2" color="textSecondary">
                  <strong>Members: </strong> {member.userId?.name}
                </Typography>
              </CardContainer>
            );
          })}

          {/* Button */}
          <JoinButton
            variant="contained"
            onClick={() => handleClick(dataRow.crewId)}
            style={{
              backgroundColor: "red",
            }}
          >
            Leave Crew
          </JoinButton>
        </StyledCardContent>
      </StyledCard>
    ));
  };

  return (
    <Box
      sx={{
        mt: 5,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        overflowY: "auto",
        height: "74vh",
        justifyContent: crews.length === 0 ? "center" : "flex-start",
      }}
    >
      {isLoading ? (
        <LoadingBackdrop />
      ) : (
        <>
          {crews.length ? (
            <>
              <Typography variant="h4">My Crews</Typography>
              <CardContainer>{getCards(crews)}</CardContainer>
            </>
          ) : (
            <Typography variant="h4" sx={{ textAlign: "center" }}>
              You are in no crew now.
            </Typography>
          )}
        </>
      )}
    </Box>
  );
}
