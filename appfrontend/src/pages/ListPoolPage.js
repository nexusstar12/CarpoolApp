import React, { useState, useEffect, useContext } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography, Box } from "@mui/material";
import axiosInstance from "../config/axios.config";
import { UserContext } from "../App";
import { LoadingBackdrop } from "../components/LoadingData";
import { convertFromUTC } from "../utilities/convertUTCToBrowserTimeZone";
import { getBrowserTimezone } from "../utilities/getTimeZoneBrowser";
import { useNavigate } from "react-router-dom";

export default function ListPoolPage() {
  const history = useNavigate();
  const [crewCreatedPoolId, setCrewCreatedPoolId] = useState(null);
  const userContext = useContext(UserContext);
  const { profileId, userId, jwtToken } = userContext.userInfo;
  const [data, setData] = useState({});
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const getFormattedStartTime = (utcTime) => {
    let startTime = convertFromUTC(utcTime, getBrowserTimezone());
    if (!startTime) {
      return "N/A";
    }

    const date = new Date(startTime);
    const dateString = date.toLocaleDateString();
    const timeString = date.toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit",
    });

    return `${dateString} ${timeString}`;
  };

  const handleClick = async (poolId, type) => {
    if (type === "DELETE POOL") {
      try {
        setIsLoading(true);
        await axiosInstance.delete(`/pool/deletepool/${poolId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });
        const response = await axiosInstance.get(`/pool/getpools/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        setData(response.data);
        setIsLoading(false);
      } catch (error) {
        if (error.response.status >= 500) {
          history("/down");
        }
        setError(error);
        setIsLoading(false);
      }
    }
    if (type === "LEAVE POOL") {
      try {
        setIsLoading(true);
        const requestBody = { profileId, poolId };

        await axiosInstance.delete(`/pool/deletemember`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
          data: requestBody,
        });
        const response = await axiosInstance.get(`/pool/getpools/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        setData(response.data);
        setIsLoading(false);
      } catch (error) {
        if (error.response.status >= 500) {
          history("/down");
        }
        setError(error);
        setIsLoading(false);
      }
    }

    if (type === "CREATE CREW") {
      try {
        const requestBody = {
          origin_pool_id: poolId,
          creator_id: profileId,
        };

        await axiosInstance.post(`/crew/createcrew`, requestBody, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        setIsLoading(true);
        const response = await axiosInstance.get(`/pool/getpools/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        setData(response.data);
        setIsLoading(false);
      } catch (error) {
        if (error.response.status >= 500) {
          history("/down");
        }
        setError(error);
        setIsLoading(false);
      }
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);

        const response = await axiosInstance.get(`/pool/getpools/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        });

        setData(response.data);
        setIsLoading(false);
      } catch (error) {
        if (error.response.status >= 500) {
          history("/down");
        }
        setError(error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  const CardContainer = styled("div")`
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    margin-bottom: 10px;
  `;

  const StyledCard = styled(Card)`
    flex: 0 0 calc(50% - 40px);
    margin: 25px;
    padding: 30px;
    height: auto;
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

  const TimeContainer = styled("div")`
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const DetailsContainer = styled("div")`
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const getCards = (data, type) => {
    if (!data) {
      return null;
    }
    return data.map((dataRow) => (
      <StyledCard key={dataRow.poolId}>
        <StyledCardContent>
          {/* Title */}
          <CardTitle>{dataRow?.description}</CardTitle>
          {/* Time */}
          <CardContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Starting Time: </strong>{" "}
              {getFormattedStartTime(dataRow.startTime)}
            </Typography>
          </CardContainer>
          <CardContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Starting Location: </strong> {dataRow.startLocation}
            </Typography>
          </CardContainer>
          <CardContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Ending Location: </strong> {dataRow.endLocation}
            </Typography>
          </CardContainer>
          {dataRow.passengers.map((passenger) => {
            return (
              <CardContainer id={passenger.passengerId}>
                <Typography variant="body2" color="textSecondary">
                  <strong>Passenger: </strong> {passenger?.name || ""}
                </Typography>
              </CardContainer>
            );
          })}
          <CardContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Driver: </strong> {dataRow.driver?.name || ""}
            </Typography>
          </CardContainer>
          {/* Button */}

          {dataRow.crewCreated && type === "CREATE CREW" ? (
            <Typography variant="h6" color="black">
              Crew Created!
            </Typography>
          ) : (
            <JoinButton
              variant="contained"
              onClick={() => handleClick(dataRow.poolId, type)}
              style={{
                backgroundColor: type === "CREATE CREW" ? "green" : "red",
              }}
            >
              {type}
            </JoinButton>
          )}
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
        maxHeight: "80vh",
      }}
    >
      {isLoading ? (
        <LoadingBackdrop />
      ) : (
        <>
          {data?.myPools?.length ? (
            <>
              <Typography variant="h4">My Pools</Typography>
              <CardContainer>
                {getCards(data.myPools, "DELETE POOL")}
              </CardContainer>
            </>
          ) : null}

          {data?.availablePools?.length ? (
            <>
              <Typography variant="h4">Available Pools</Typography>
              <CardContainer>
                {getCards(data.availablePools, "LEAVE POOL")}
              </CardContainer>
            </>
          ) : null}

          {data?.pastPools?.length ? (
            <>
              <Typography variant="h4">Past Pools</Typography>
              <CardContainer>
                {getCards(data.pastPools, "CREATE CREW")}
              </CardContainer>
            </>
          ) : null}
        </>
      )}
    </Box>
  );
}
