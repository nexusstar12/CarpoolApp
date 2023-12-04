import React, { useState } from 'react';

const TeamMember = ({ name, role, imageUrl }) => {
  const [clicked, setClicked] = useState(false);

  return (
    <div
      className={`team-member ${clicked ? 'clicked' : ''}`}
      onClick={() => setClicked(!clicked)}
    >
      <img src={imageUrl} alt={name} />
      {clicked && (
        <div className="overlay">
          <div className="overlay-content">
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
    { name: 'Isiah', role: 'Frontend engineering plus UX and design artifacts', imageUrl: '' },
    { name: 'Jonathan', role: 'Fullstack engineering plus database design and architecture', imageUrl: '../utilities/kendrickImage.jpg' },
    { name: 'Kendrick', role: 'Frontend engineering plus systems diagrams and documentation', imageUrl: '' },
    { name: 'Kristian', role: 'Backend engineering plus API endpoint design and implementation', imageUrl: '' },
    { name: 'Phillip', role: 'Backend engineering plus greenfield architecture and implementation', imageUrl: '' },
    { name: 'Xuan', role: 'Frontend engineering plus devops and deployment pipeline', imageUrl: '' },
    { name: 'Zoe', role: 'Product and Team Lead', imageUrl: '' },
  ];

  return (
    <div className="scrollable-container about-page">
      <h1>About the Pool Team</h1>
      <h2>More Information</h2>
      <p style={{ paddingLeft: '60px', paddingRight: '60px' }}>
        Pool was created by a team of San Francisco State University undergrad and graduate students.
        Learn about us by clicking on a picture below. Art by Isiah Paul McGlothin
      </p>

      <div className="team-grid">
        {teamMembers.map((member, index) => (
          <TeamMember key={index} {...member} />
        ))}
      </div>
    </div>
  );
}

export default AboutPage;
