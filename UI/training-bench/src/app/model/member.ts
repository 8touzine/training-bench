export class Member {

    private id?: number;
    private nom: string;
    private email: string;

    constructor(nom: string, email: string){
        this.nom = nom;
        this.email = email;
    }
    
    public getName(): string{
        return this.nom;
    }   
    public setName(nom: string){
        this.nom = nom;

    }
    public getEmail(): string{
        return this.email;
    }   
    public setEmail(email: string){
        this.email = email;

    }

}