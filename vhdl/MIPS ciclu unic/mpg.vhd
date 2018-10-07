library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity mpg is
    Port ( btn : in STD_LOGIC;
           clk : in STD_LOGIC;
           enable : out STD_LOGIC);
end mpg;

architecture Behavioral of mpg is
SIGNAL nr_int : STD_LOGIC_VECTOR(15 downto 0) := (others => '0');
SIGNAL Q1 : STD_LOGIC := '0';
SIGNAL Q2 : STD_LOGIC := '0';
SIGNAL Q3 : STD_LOGIC := '0';


begin

    process(clk)
    begin
        if rising_edge(clk) then
            nr_int <= nr_int + 1;
        end if;    
    end process;
    
    
    process(clk)
    begin
        if rising_edge(clk) then
            if nr_int = x"FFFF" then
                Q1 <= btn;
            end if;
        end if;
    end process;
    
    
    process(clk)
    begin
        if rising_edge(clk) then
            Q3 <= Q2;
            Q2 <= Q1;
        end if;
    end process;
    
    enable <= Q2 AND (NOT Q3);  


end Behavioral;
