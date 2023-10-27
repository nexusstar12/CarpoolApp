import React, { useState } from "react";
import styled from "@emotion/styled";
import { Button, Card, CardContent, Typography } from "@mui/material";
import Modal from "@mui/material/Modal";

export const SearchResult = ({ result, itemsPerPage }) => {
  // const [currentPage, setCurrentPage] = useState(1);
  const [isModalOpen, setModalOpen] = useState(false);
  const [disabledButtons, setDisabledButtons] = useState([]);
  console.log("disabledButtons", disabledButtons);
  const handleJoinPoolClick = (poolId) => {
    setModalOpen(true);

    setDisabledButtons((prevState) => {
      const newState = [...prevState];
      newState[poolId] = true;
      return newState;
    });
  };

  const handleCloseModal = () => {
    setModalOpen(false);
  };

  const renderModal = () => (
    <Modal open={isModalOpen} onClose={handleCloseModal}>
      <div
        style={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          backgroundColor: "white",
          padding: "1em",
          border: "1px solid #ccc",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
        }}
      >
        <h2>Join Pool Coming Soon</h2>
        <button onClick={handleCloseModal}>Close</button>
      </div>
    </Modal>
  );

  // const indexOfLastItem = currentPage * itemsPerPage;
  // const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  // const currentItems = result.slice(indexOfFirstItem, indexOfLastItem);

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
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const DetailsContainer = styled("div")`
    margin-bottom: 10px;
    font-size: 0.9em;
  `;

  const getCards = () => {
    return result.map((data, index) => (
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
            disabled={disabledButtons[data.poolId]}
          >
            {disabledButtons[data.poolId] ? "JOINED" : "JOIN POOL"}
          </JoinButton>
        </StyledCardContent>
      </StyledCard>
    ));
  };

  // const paginate = (pageNumber) => {
  //   setCurrentPage(pageNumber);
  // };

  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(result.length / itemsPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <>
      {/* {renderModal()} */}
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
