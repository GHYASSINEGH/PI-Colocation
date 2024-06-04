export interface Announcement {
    id: number;
    title: string;
    content: string;
    location :string;
    posteDate: Date;
   likes:BigInteger;
    photo:string;
    photoUrl:string;
    user:any;


 }