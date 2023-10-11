// ButtonComponent.js

import React from 'react';
import { Link } from 'react-router-dom';

const ButtonComponent = () => {
  return (
    <div>
      <p>Click the button to go to the New Page:</p>
      <Link to="/new">
        <button>Go to New Page</button>
      </Link>
    </div>
  );
};

export default ButtonComponent;
