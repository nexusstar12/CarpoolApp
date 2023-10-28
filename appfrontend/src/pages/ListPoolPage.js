import React, { useState, useEffect } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography, Box } from "@mui/material";
import axiosInstance from "../config/axios.config";

export default function ListPoolPage() {
  const [data, setData] = useState({
    myPools: [
      {
        poolId: 1,
        name: "FirstPool",
        startLocation: "1717 Greenwich St, San Francisco, CA, 94123",
        endLocation: "2222 Broderick St, San Francisco, CA, 94115",
        startTime: "2023-10-18 00:00:00",
        driver: {
          driverId: 3,
          name: "Zoe",
        },
        passengers: [
          {
            userId: 2,
            name: "Xuan Duy Anh",
          },
          {
            userId: 3,
            name: "Kristian",
          },
        ],
      },
    ],
    availablePools: [
      {
        poolId: 11,
        name: "SecondPool",
        startLocation: "1717 Greenwich St, San Francisco, CA, 94123",
        endLocaltion: "2222 Broderick St, San Francisco, CA, 94115",
        startTime: "2023-10-18 00:00:00",
        driver: {
          driverId: 3,
          name: "Zoe",
        },
        passengers: [
          {
            userId: 2,
            name: "Xuan Duy Anh",
          },
          {
            userId: 3,
            name: "Kristian",
          },
        ],
      },
    ],
    pastPool: [
      {
        poolId: 111,
        name: "ThirdPool",
        startLocation: "1717 Greenwich St, San Francisco, CA, 94123",
        endLocaltion: "2222 Broderick St, San Francisco, CA, 94115",
        startTime: "2023-10-18 00:00:00",
        driver: {
          driverId: 3,
          name: "Zoe",
        },
        passengers: [
          {
            userId: 2,
            name: "Xuan Duy Anh",
          },
          {
            userId: 3,
            name: "Zoe Zoe",
          },
          {
            userId: 3,
            name: "Lam",
          },
        ],
      },
    ],
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [disabledButtons, setDisabledButtons] = useState([]);

  const handleClick = (poolId, type) => {
    console.log("type", type);
    if (type === "LEAVE POOL") {
      const updatedMyPools = data.myPools.filter(
        (item) => item.poolId !== poolId
      );
      setData((prevData) => ({ ...prevData, myPools: updatedMyPools }));
    }
    if (type === "JOIN POOL") {
    }
    console.log("data", data);
  };

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axiosInstance.get(
  //         "/searchbar?filter=endZip&value=94115"
  //       );
  //       setData(response.data);
  //     } catch (err) {
  //       setError(err);
  //     }
  //   };

  //   fetchData();
  // }, []);

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  const CardContainer = styled("div")`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
  `;

  const StyledCard = styled(Card)`
    flex: 0 0 calc(100% - 20px);
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
    console.log("datadata", data);
    if (!data) {
      return (
        <StyledCard>
          <StyledCardContent>
            <DetailsContainer>
              <Typography variant="body2" color="textSecondary">
                <strong>Starting Location: </strong>
              </Typography>
            </DetailsContainer>
          </StyledCardContent>
        </StyledCard>
      );
    }
    return data.map((dataRow) => (
      <StyledCard key={dataRow.poolId}>
        <StyledCardContent>
          {/* Title */}
          <CardTitle>{dataRow?.name}</CardTitle>

          {/* Time */}
          <TimeContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Starts: </strong> {dataRow.startTime || "N/A"}
            </Typography>
          </TimeContainer>

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
              <CardContainer>
                <Typography variant="body2" color="textSecondary">
                  <strong>Passenger: </strong> {passenger.name}
                </Typography>
              </CardContainer>
            );
          })}

          <CardContainer>
            <Typography variant="body2" color="textSecondary">
              <strong>Driver: </strong> {dataRow.driver.name}
            </Typography>
          </CardContainer>

          {/* Button */}
          <JoinButton
            variant="contained"
            onClick={() => handleClick(dataRow.poolId, type)}
            style={{
              backgroundColor: type === "LEAVE POOL" ? "red" : "green",
            }}
          >
            {type}
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
        maxHeight: "80vh",
      }}
    >
      {data.myPools.length ? (
        <Typography variant="h4">My Pools</Typography>
      ) : null}
      <CardContainer>{getCards(data.myPools, "LEAVE POOL")}</CardContainer>
      <Typography variant="h4">Available Pools</Typography>
      <CardContainer>
        {getCards(data.availablePools, "JOIN POOL")}
      </CardContainer>
      <Typography variant="h4">Past Pools</Typography>
      <CardContainer>{getCards(data.pastPool, "CREATE CREW")}</CardContainer>
      {/* <div>
        {data.length === 0 ? (
          <p>No results found.</p>
        ) : (
          <CardContainer>{getCards()}</CardContainer>
        )}
      </div> */}
    </Box>
  );
}
