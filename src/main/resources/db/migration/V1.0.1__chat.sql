/*        id:
          type: integer
          format: int64
        dateCreated:
          type: string
          format: date-time
        dateModified:
          type: string
          format: date-time
        memberIds:
          type: array
          items:
            type: integer
            format: int64 */

create table chat (
  id bigint primary key,
  date_created timestamp,
  date_modified timestamp
);