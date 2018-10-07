library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity RAM is
    Port ( address : in STD_LOGIC_VECTOR (15 downto 0);
           wd : in STD_LOGIC_VECTOR (15 downto 0);
           we : in STD_LOGIC;
           clk : in STD_LOGIC;
           rd : out STD_LOGIC_VECTOR (15 downto 0));
end RAM;

architecture Behavioral of RAM is
type memorie is array (0 to 63) of std_logic_vector (15 downto 0);
signal mem: memorie := (
0=> x"000f",
1=> x"0001",
2=> x"0002",
3=> x"0003",
4=>x"0004",
5=> x"0005",
others => x"9999"
);

begin
    process(clk,we) 
    begin
        if rising_edge(clk) then
            if we='1'  then
                mem(conv_integer(address(5 downto 0)))<=wd;
            end if;
        end if;
    end process;
    
     rd<=mem(conv_integer(address(5 downto 0)));

end Behavioral;
