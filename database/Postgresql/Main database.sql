CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(40),
  "email" varchar(115),
  "password" varchar(255),
  "token" varchar(255),
  "image_url" varchar(120),
  "verified_at" timestamp,
  "created_at" timestamp
);

CREATE TABLE "tournaments" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(80),
  "start_date" date,
  "end_date" date,
  "is_public" boolean,
  "image_url" varchar(100),
  "created_by" bigint,
  "created_at" timestamp
);

CREATE TABLE "tournaments_phase_types" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(50)
);

CREATE TABLE "tournaments_phases" (
  "tournament_id" bigint,
  "phase_type_id" int,
  PRIMARY KEY ("tournament_id", "phase_type_id")
);

CREATE TABLE "regions" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "image_url" varchar
);

CREATE TABLE "teams" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(50),
  "image_url" varchar(100),
  "region_id" int
);

CREATE TABLE "matches" (
  "id" BIGSERIAL PRIMARY KEY,
  "first_team_id" bigint,
  "second_team_id" bigint,
  "match_date" datetime
);

CREATE TABLE "match_results" (
  "match_id" bigint PRIMARY KEY,
  "first_team_score" int2,
  "second_team_score" int2
);

CREATE TABLE "pickems" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar(100),
  "is_public" boolean,
  "created_by" bigint,
  "created_at" timestamp
);

CREATE TABLE "pickem_tournament_phase" (
  "pickem_id" bigint,
  "tournament_id" bigint,
  "phase_type_id" int,
  PRIMARY KEY ("pickem_id", "tournament_id", "phase_type_id")
);

CREATE TABLE "participants" (
  "pickem_id" bigint,
  "user_id" bigint,
  "current_score" int,
  PRIMARY KEY ("pickem_id", "user_id")
);

CREATE TABLE "tournament_rank" (
  "tournament_id" bigint,
  "user_id" bigint,
  "vote" boolean,
  PRIMARY KEY ("tournament_id", "user_id")
);

ALTER TABLE "tournaments_phases" ADD FOREIGN KEY ("tournament_id") REFERENCES "tournaments" ("id");

ALTER TABLE "tournaments_phases" ADD FOREIGN KEY ("phase_type_id") REFERENCES "tournaments_phase_types" ("id");

ALTER TABLE "tournaments" ADD FOREIGN KEY ("created_by") REFERENCES "users" ("id");

ALTER TABLE "teams" ADD FOREIGN KEY ("region_id") REFERENCES "regions" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("first_team_id") REFERENCES "teams" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("second_team_id") REFERENCES "teams" ("id");

ALTER TABLE "match_results" ADD FOREIGN KEY ("match_id") REFERENCES "matches" ("id");

ALTER TABLE "pickem_tournament_phase" ADD FOREIGN KEY ("pickem_id") REFERENCES "pickems" ("id");

ALTER TABLE "pickem_tournament_phase" ADD FOREIGN KEY ("tournament_id") REFERENCES "tournaments_phases" ("tournament_id");

ALTER TABLE "pickem_tournament_phase" ADD FOREIGN KEY ("phase_type_id") REFERENCES "tournaments_phases" ("phase_type_id");

ALTER TABLE "participants" ADD FOREIGN KEY ("pickem_id") REFERENCES "pickems" ("id");

ALTER TABLE "participants" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("group_id") REFERENCES "groups" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_group_prediction" ADD FOREIGN KEY ("team_id") REFERENCES "teams" ("id");

ALTER TABLE "tournament_rank" ADD FOREIGN KEY ("tournament_id") REFERENCES "tournaments" ("id");

ALTER TABLE "tournament_rank" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");
