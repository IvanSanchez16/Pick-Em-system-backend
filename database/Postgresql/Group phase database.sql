CREATE TABLE "groups" (
  "id" bigint PRIMARY KEY,
  "tournament_id" bigint,
  "char_name" char
);

CREATE TABLE "groups_teams" (
  "group_id" bigint,
  "team_id" bigint,
  "pool" int2,
  PRIMARY KEY ("group_id", "team_id")
);

CREATE TABLE "group_matches" (
  "group_id" bigint,
  "match_id" int,
  PRIMARY KEY ("group_id", "match_id")
);

CREATE TABLE "user_group_prediction" (
  "group_id" bigint,
  "user_id" bigint,
  "team_id" bigint,
  "position" smallint,
  PRIMARY KEY ("group_id", "user_id", "team_id")
);

ALTER TABLE "groups" ADD FOREIGN KEY ("tournament_id") REFERENCES "tournaments" ("id");

ALTER TABLE "groups_teams" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "groups_teams" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "group_matches" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "group_matches" ADD FOREIGN KEY ("match_id") REFERENCES "matches" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");