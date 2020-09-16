db = db.getSiblingDB("admin");
db.createUser(
    {
      user: "rockstars-test",
      pwd: "secureTestPassword",
      roles: [
        {
          role: "readWrite",
          db: "rockstars"
        }
      ]
    }
);

