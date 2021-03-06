package org.example.csemall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("offerDao")
public class OfferDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getRowCount(){

        String sqlStatement = "SELECT count(*) FROM offers";
        return jdbcTemplate.queryForObject(sqlStatement, Integer.class);
    }

    // query and return a single object
    public Offer getOffer(String name){
        String sqlStatement = "select * from offers where name=?"; // ?는 placeholder

        return jdbcTemplate.queryForObject(sqlStatement, new Object[]{name}, new RowMapper<Offer>() {
            @Override
            public Offer mapRow(ResultSet resultSet, int i) throws SQLException {

                Offer offer = new Offer();

                offer.setId(resultSet.getInt("id"));
                offer.setName(resultSet.getString("name"));
                offer.setEmail(resultSet.getString("email"));
                offer.setText(resultSet.getString("text"));

                return offer;
            }
        });

    }

    // query and return multiple object
    public List<Offer> getOffers(){
        String sqlStatement = "select * from offers"; // ?는 placeholder

        return jdbcTemplate.query(sqlStatement, new RowMapper<Offer>() {
            @Override
            public Offer mapRow(ResultSet resultSet, int i) throws SQLException {

                Offer offer = new Offer();

                offer.setId(resultSet.getInt("id"));
                offer.setName(resultSet.getString("name"));
                offer.setEmail(resultSet.getString("email"));
                offer.setText(resultSet.getString("text"));

                return offer;
            }
        });

    }

    public boolean insert(Offer offer){
        String name = offer.getName();
        String email = offer.getEmail();
        String text = offer.getText();

        String sqlStatement = "insert into offers (name, email, text) values (?,?,?)";

        return (jdbcTemplate.update(sqlStatement,new Object[] {name, email, text})==1);
    }

    public boolean update(Offer offer){
        int id = offer.getId();
        String name = offer.getName();
        String email = offer.getEmail();
        String text = offer.getText();

        String sqlStatement = "update offers set name=?, email=?, text=? where id=?";

        return (jdbcTemplate.update(sqlStatement,new Object[] {name, email, text, id})==1);
    }

    public boolean delete(int id){
        String sqlStatement = "delete from offers where id=?";

        return (jdbcTemplate.update(sqlStatement,new Object[] {id})==1);
    }
}
