import React, { useState, useEffect } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography, Box } from "@mui/material";
import axiosInstance from "../config/axios.config";

export default function ListCrewPage() {
  const [data, setData] = useState(
     [
      {
        crewId: 1,
        name: "FirstCrew",
        members: [
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
      {
        crewId: 1,
        name: "FirstCrew",
        members: [
          {
            userId: 2,
            name: "Xuan Duy Anh",
          },
          {
            userId: 3,
            name: "Kristian",
          },
        ],
      },{
        crewId: 1,
        name: "FirstCrew",
        members: [
          {
            userId: 2,
            name: "Xuan Duy Anh",
          },
          {
            userId: 3,
            name: "Kristian",
          },
        ],
      },{
        crewId: 1,
        name: "FirstCrew",
        members: [
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
    ]
  );

console.log("data",data)
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [disabledButtons, setDisabledButtons] = useState([]);

  const handleClick = (crewId, type) => {
    console.log("type", type);
    if (type === "LEAVE CREW") {
      const updatedMyCrews = data.myCrews.filter(
        (item) => item.crewId !==crewId
      );
      setData((prevData) => ({ ...prevData, myCrews: updatedMyCrews }));
    }
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
    console.log("datadata", data);
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
        // console.log("dataRow",dataRow)
      <StyledCard key={dataRow.crewId}>
        <StyledCardContent>
          {/* Title */}
          <CardTitle>{dataRow?.name}</CardTitle>

          {dataRow.members.map((member) => {
            return (
              <CardContainer>
                <Typography variant="body2" color="textSecondary">
                  <strong>Members: </strong> {member.name}
                </Typography>
              </CardContainer>
            );
          })}

          {/* Button */}
          <JoinButton
            variant="contained"
            onClick={() => handleClick(dataRow.crewId)}
            style={{
                backgroundColor: "red"
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
        maxHeight: "80vh",
      }}
    >
      {data.length ? (
        <Typography variant="h4">My Crews</Typography>
      ) : null}
      <CardContainer>{getCards(data)}</CardContainer>
 
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
