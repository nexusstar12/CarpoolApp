import { Box } from "@mui/material";
import React, { useState } from "react";

const TeamMember = ({ name, role, imageUrl }) => {
  const [clicked, setClicked] = useState(false);

  return (
    <div
      style={{ marginBottom: "80px" }}
      className="team-member"
      onClick={() => setClicked(!clicked)}
    >
      <img
        className="team-member-image"
        src={imageUrl}
        alt={name}
        style={{ height: "200px", width: "200px", objectFit: "contain" }}
      />
      {clicked && (
        <div>
          <div>
            <h3>{name}</h3>
            <p>{role}</p>
          </div>
        </div>
      )}
    </div>
  );
};

function AboutPage() {
  const teamMembers = [
    {
      name: "Isiah",
      role: "Frontend engineering plus UX and design artifacts",
      imageUrl: "isiahImage.jpg",
    },
    {
      name: "Jonathan",
      role: "Fullstack engineering plus database design and architecture",
      imageUrl: "jonathanImage.jpg",
    },
    {
      name: "Kendrick",
      role: "Frontend engineering plus systems diagrams and documentation",
      imageUrl: "kendrickImage.jpg",
    },
    {
      name: "Kristian",
      role: "Backend engineering plus API endpoint design and implementation",
      imageUrl: "kristianImage.jpg",
    },
    {
      name: "Phillip",
      role: "Backend engineering plus greenfield architecture and implementation",
      imageUrl: "phillipImage.jpg",
    },
    {
      name: "Xuan",
      role: "Frontend engineering plus devops and deployment pipeline",
      imageUrl: "xuanImage.jpg",
    },
    { name: "Zoe", role: "Product and Team Lead", imageUrl: "zoeImage.jpg" },
  ];

  return (
    <Box
      sx={{
        mt: 5,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        overflowY: "auto",
        maxHeight: "74vh",
      }}
    >
      <div className="scrollable-container about-page">
        <h1>About the Pool Team</h1>
        <h2>More Information</h2>
        <p style={{ paddingLeft: "60px", paddingRight: "60px" }}>
          Pool was created by a team of San Francisco State University undergrad
          and graduate students. Learn about us by clicking on a picture below.
          Art by Isiah Paul McGlothin
        </p>

        <div className="team-grid">
          {teamMembers.map((member, index) => (
            <TeamMember key={index} {...member} />
          ))}
        </div>
      </div>
    </Box>
  );
}

export default AboutPage;
