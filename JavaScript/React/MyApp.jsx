function handleClick() {
  alert("I've been clicked!");
}

function Buttion() {
  return <button onClick={handleClick}>Click me!</button>;
}

function AboutPage() {
  return (
    <>
      <h1>About</h1>
      <p>
        Hello there.
        <br />
        How do you do?
      </p>
    </>
  );
}

function MyApp() {
  return (
    <div>
      <h1>Welcome to my app</h1>
      <Buttion />
      <AboutPage />
    </div>
  );
}
