/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;


/**
 * Clase que representa una relaci�n de amistad entre dos usuarios.
 */
public class Relation {
    private User user1;
    private User user2;
    private Integer tiempoAmistad;

    /**
     * Constructor de la clase Relation.
     *
     * @param user1          El primer usuario de la relaci�n.
     * @param user2          El segundo usuario de la relaci�n.
     * @param tiempoAmistad  El tiempo de amistad entre los usuarios.
     */
    public Relation(User user1, User user2, Integer tiempoAmistad) {
        this.user1 = user1;
        this.user2 = user2;
        this.tiempoAmistad = tiempoAmistad;
    }

    /**
     * M�todo que devuelve el tiempo de amistad entre los usuarios.
     *
     * @return El tiempo de amistad.
     */
    public int getFriendshipTime() {
        return tiempoAmistad;
    }

    /**
     * M�todo que devuelve el primer usuario de la relaci�n.
     *
     * @return El primer usuario.
     */
    public User getUser1() {
        return user1;
    }

    /**
     * M�todo que devuelve el segundo usuario de la relaci�n.
     *
     * @return El segundo usuario.
     */
    public User getUser2() {
        return user2;
    }

    /**
     * M�todo que verifica si la relaci�n contiene al usuario especificado.
     *
     * @param user El usuario a verificar.
     * @return `true` si la relaci�n contiene al usuario, `false` de lo contrario.
     */
    public boolean containsUser(User user) {
        return user1.equals(user) || user2.equals(user);
    }

    /**
     * M�todo que verifica si la relaci�n contiene a los usuarios especificados.
     *
     * @param user1 El primer usuario a verificar.
     * @param user2 El segundo usuario a verificar.
     * @return `true` si la relaci�n contiene a los usuarios, `false` de lo contrario.
     */
    public boolean containsUsers(User user1, User user2) {
        return (this.user1.equals(user1) && this.user2.equals(user2)) || (this.user2.equals(user1) && this.user1.equals(user2));
    }

    /**
     * M�todo que devuelve el otro usuario de la relaci�n.
     *
     * @param user El usuario actual.
     * @return El otro usuario de la relaci�n, o `null` si el usuario no est� involucrado en la relaci�n.
     */
    public User getOtherUser(User user) {
        if (user.equals(user1)) {
            return user2;
        } else if (user.equals(user2)) {
            return user1;
        }
        // Si el user no est� involucrado en la relaci�n, se retorna null
        return null;
    }
}
