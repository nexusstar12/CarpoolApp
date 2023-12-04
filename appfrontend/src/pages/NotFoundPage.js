import Link from "@mui/material/Link";

const NotFound = () => {
  return (
    <div
      style={{
        textAlign: "center",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      {/* Centers the content inside the div */}
      <Link
        className="not-found-container"
        href="/"
        style={{
          fontSize: "24px", // Adjust the font size as needed
          marginTop: "200px", // Optional, for adjusting vertical spacing
        }}
      >
        You’ve found a page which doesn’t exist. Click here to return home.
      </Link>
    </div>
  );
};

export default NotFound;
