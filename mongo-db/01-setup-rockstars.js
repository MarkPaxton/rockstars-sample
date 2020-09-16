db = db.getSiblingDB("admin");
db.createUser(
    {
      user: "rockstars",
      pwd: "securePassword",
      roles: [
        {
          role: "readWrite",
          db: "rockstars"
        }
      ]
    }
);
