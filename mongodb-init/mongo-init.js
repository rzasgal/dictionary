
db = new Mongo().getDB("dictionary");

db.createCollection('entry', { capped: false });

db.createUser(
    {
        user: "dictuser",
        pwd: "test",
        roles: [
            {
                role: "readWrite",
                db: "dictionary"
            }
        ]
    }
);
